# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-27 11:53:10 UTC+08:00
"""

from __future__ import annotations

import hashlib
import os
import time
import typing as t

from charset_normalizer import md
from huggingface_hub.utils.insecure_hashlib import md5
from langchain_chroma import Chroma
from streamlit.runtime.uploaded_file_manager import UploadedFile
from langchain_text_splitters import RecursiveCharacterTextSplitter

from model.config import Config
from utils.config import ConfigUtils
from vector.chroma import ChromaVectorStorageManager


class KnowledgeService:
    config: t.ClassVar[Config] = ConfigUtils.get_config()

    def __init__(self):
        self.chrmoa: Chroma = ChromaVectorStorageManager.get_chroma("wardrobe-mind")
        self.spliter = RecursiveCharacterTextSplitter(
            chunk_size=self.config.splitter.chunk,  # 分割后的文本最大长度
            chunk_overlap=self.config.splitter.overlap,  # 连续文本段之间的字符重叠数量
            separators=self.config.splitter.separator,  # 自然段落划分的符号
            length_function=len,
        )

    def upload(self, file: UploadedFile):
        text = file.getvalue().decode("UTF-8")
        text_md5 = self.md5(text)

        if self.check_md5(text_md5):
            return "文件已存在"

        knowledge_chunks: list[str] = []
        if len(text) > self.config.splitter.max_char:
            knowledge_chunks = self.spliter.split_text(text)
        else:
            knowledge_chunks = [text]

        metadata = {
            "name": file.name,
            "size": len(text),
            "md5": text_md5,
            "create_at": time.time(),
            "author": "大王",
        }

        self.chrmoa.add_texts(knowledge_chunks, metadatas=[metadata for _ in knowledge_chunks])
        self.save_md5(text_md5, "md5")
        return "上传成功"

    @classmethod
    def check_md5(cls, md5: str) -> bool:
        if not os.path.exists(cls.config.md5_path):
            return False

        with open(cls.config.md5_path, "r") as stream:
            for line in stream.readlines():
                if line.strip() == md5:
                    return True

        return False

    @classmethod
    def save_md5(cls, input: str, type: t.Literal["text", "md5"] = "text"):
        if type == "text":
            md5 = cls.md5(input)
        elif type == "md5":
            md5 = input
        else:
            raise RuntimeError("类型错误")

        if not cls.check_md5(md5):
            with open(cls.config.md5_path, "a+", encoding="UTF-8") as stream:
                stream.write(f"{md5}\n")

    @classmethod
    def md5(cls, input: str) -> str:
        return hashlib.md5(input.encode("UTF-8")).hexdigest()

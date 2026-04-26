# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-25 23:15:40 UTC+08:00
"""
from __future__ import annotations

import typing as t

import orjson
from langchain_core.documents import Document

from common import ROOT_DIR
from documents.csv import DocumentCSVLoader
from documents.json import DocumentJSONLoader
from documents.pdf import DocumentPDFLoader
from documents.text import DocumentTextLoader
from documents.splitter.text import TextSplitter


def csv_loader():
    loader = DocumentCSVLoader.get_loader(ROOT_DIR / "resources" / "stu.csv")

    content = loader.load()

    for row in content:
        print(row.page_content)


def json_default_loader():
    loader = DocumentJSONLoader.get_loader(ROOT_DIR / "resources" / "stu.json")

    content = loader.load()

    for row in content:
        data = orjson.loads(row.page_content)
        print(type(data), data)


def json_lines_loader():
    loader = DocumentJSONLoader.get_loader(ROOT_DIR / "resources" / "stu_json_lines.json", json_lines=True)

    content = loader.load()

    for row in content:
        data = orjson.loads(row.page_content)
        print(type(data), data)


def text_loader():
    loader = DocumentTextLoader.get_loader(ROOT_DIR / "resources" / "Python基础语法.txt")
    splitter = TextSplitter.get_splitter()

    documents = loader.load()

    split_docs: list[Document] = splitter.split_documents(documents)

    for doc in split_docs:
        print(doc.page_content)


def pdf_loader():
    loader = DocumentPDFLoader.get_loader(ROOT_DIR / "resources" / "pdf1.pdf")

    for doc in loader.lazy_load():
        print("=" * 80)
        print(doc.page_content)
        print("=" * 80)


if __name__ == '__main__':
    # csv_loader()
    # json_default_loader()
    # json_lines_loader()
    # text_loader()
    pdf_loader()

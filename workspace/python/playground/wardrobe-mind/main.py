# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-27 11:33:32 UTC+08:00
"""

from __future__ import annotations

import time
import typing as t

import streamlit as st
from streamlit.runtime.uploaded_file_manager import UploadedFile

from service.knowledge import KnowledgeService
from agent.wardrobe import wardrobe_mind_agent


def server():
    st.title("知识库更新服务")

    upload_file: UploadedFile = t.cast(
        UploadedFile,
        st.file_uploader(
            "请选择要上传的文件",
            type=[
                "txt",
            ],
            accept_multiple_files=False,
        ),
    )

    if "service" not in st.session_state:
        st.session_state.service = KnowledgeService()

    if upload_file is not None:
        file_name = upload_file.name
        file_type = upload_file.type
        file_size = upload_file.size / 1024  # KB

        st.subheader(f"文件名：{file_name}")
        st.write(f"格式：{file_type} | 大小：{file_size:.2f} KB")

        text = upload_file.getvalue().decode("UTF-8")

        with st.spinner("正在上传文件"):
            time.sleep(1)
            result = st.session_state.service.upload(upload_file)
            st.write(f"上传结果：{result}")


def client():
    def capture_output(generator: t.Generator[str, None, None], output_list: list[str]) -> t.Generator[str, None, None]:
        for output in generator:
            output_list.append(output)
            yield output

    st.title("知识库查询服务")
    st.divider()

    if "message" not in st.session_state:
        st.session_state.message = [{"role": "assistant", "content": "您好！我是您的智能助手，请问有什么可以帮助您的吗？"}]

    for message in st.session_state.get("message", []):
        st.chat_message(message["role"]).write(message["content"])

    prompt = st.chat_input("请输入查询内容")
    if prompt:
        st.chat_message("user").write(prompt)
        st.session_state.message.append({"role": "user", "content": prompt.strip()})

        messages: list[str] = []
        with st.spinner("思考中..."):
            output = wardrobe_mind_agent({"input": prompt.strip()})
            st.chat_message("assistant").write_stream(capture_output(output, messages))
            st.session_state.message.append({"role": "assistant", "content": "".join(messages)})


if __name__ == "__main__":
    client()

# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-24 13:12:05 UTC+08:00
"""
from __future__ import annotations

import typing as t

from common.enum.gender import GenderEnum

from langchain_core.prompts import PromptTemplate


def generate_name_prompt(gender: GenderEnum) -> str:
    prompt_template = PromptTemplate.from_template("生成一个{gender}孩子的名字")

    return prompt_template.invoke(input={"gender": gender.value}).to_string()

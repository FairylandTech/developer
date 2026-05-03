# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-23 01:02:55 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_core.prompts import FewShotPromptTemplate, PromptTemplate


def antonym_prompt(word: str) -> str:
    exmaple_template: PromptTemplate = PromptTemplate.from_template("单词：{word}，反义词：{antonym}")

    examples: list[dict[str, str]] = [
        {"word": "上", "antonym": "下"},
        {"word": "大", "antonym": "小"}
    ]

    few_shot_prompt: FewShotPromptTemplate = FewShotPromptTemplate(
        example_prompt=exmaple_template,
        examples=examples,
        prefix="给出给定单词的反义词：",
        suffix="基于以上例子，{input_word}单词的反义词是？",
        input_variables=["input_word", ]
    )

    return few_shot_prompt.invoke(input={"input_word": word}).to_string()

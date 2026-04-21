# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-17 03:47:48 UTC+08:00
"""
from __future__ import annotations

import typing as t

from langchain_core.tools import tool
from langchain_tavily import TavilySearch

tavily = TavilySearch(
    max_results=3,
    topic="general",
    # include_answer=False,
    # include_raw_content=False,
    # include_images=False,
    # include_image_descriptions=False,
    # search_depth="basic",
    # time_range="day",
    # start_date=None,
    # end_date=None,
    # include_domains=None,
    # exclude_domains=None,
    # include_usage= False
)


@tool
def tavily_search(keyword: str):
    """
    使用 Tavily 进行搜索。

    :param keyword: 搜索关键词
    :type keyword: str
    :return:
    :rtype:
    """
    return tavily.invoke(keyword)

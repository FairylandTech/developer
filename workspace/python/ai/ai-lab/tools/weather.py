# coding: UTF-8
"""
@software: PyCharm
@author: Beau Dean
@contact: https://fairy.host
@organization: https://github.com/FairylandFuture
@datetime: 2026-04-15 01:25:43 UTC+08:00
"""
from __future__ import annotations

import os
from pathlib import Path

import requests
from langchain.tools import tool

from domain.schema.tool import WeatherLiveToolInput
from utils.qweather import QWeatherUtils


@tool(args_schema=WeatherLiveToolInput, description="获取实时天气信息")
def get_weather_live(location: str) -> str:
    """
    获取实时天气信息。

    :param location: 地理位置，城市名称
    :type location: str
    :return: 实时天气信息
    :rtype: str
    """

    # 和风天气：me5n8ne5bq.re.qweatherapi.com

    pem = Path(os.path.join(Path.home(), ".ssh", "ed25519-private.pem"))

    headers = {
        "Authorization": f"Bearer {QWeatherUtils.generate_token(pem)}",
        "Content-Type": "application/json",
    }

    location_response = requests.get(
        "https://me5n8ne5bq.re.qweatherapi.com/geo/v2/city/lookup",
        params={"location": location, "number": 1},
        headers=headers,
        timeout=10,
        verify=False,
    )
    location_response.raise_for_status()
    location_id = location_response.json().get("location", [{}])[0].get("id", None)

    if not location_id:
        return f"{location}的地理位置信息未找到。"

    weather_response = requests.get(
        "https://me5n8ne5bq.re.qweatherapi.com/v7/weather/now",
        params={"location": location_id},
        headers=headers,
        timeout=10,
        verify=False,
    )
    weather_response.raise_for_status()
    weather = weather_response.json().get("now", {}).get("text", "unknown")

    if not weather:
        return f"{location}的天气信息未找到。"

    return f"查询到{location}的实时天气信息：{weather}。"

function operator(proxies) {
  const vendors = {
    bwh: 'S01',
    fuidc: 'S11',
    raksmart: 'S12',
    aws: 'S21',
  };

  const protocols = {
    vless: 'P01',
    anytls: 'P02',
  };

  // 地区后可带线路编号（如 02）和 NAT，线路支持 ISP (...)、Native 和 Transfer ...
  const pattern = /^(?:3X-UI|S-UI)\s+(\S+)\s+(\S+)\s+(\S+)(?:\s+(\d+))?\s+(?:NAT\s+)?(?:ISP\s*[（(]\s*([^/()（）]+?)(?:\s*\/[^()（）]+)?\s*[)）]|(Native|Transfer\s+.+))$/i;
  const seen = new Map();

  for (const p of proxies) {
    const titles = [p._subDisplayName, p._subName].filter(Boolean);
    const match = titles
      .map(title => title.trim().match(pattern))
      .find(Boolean);

    if (!match) {
      throw new Error('订阅标题格式不匹配：' + (titles.join(' / ') || '缺少来源名称'));
    }

    const [, vendor, country, region, lineNumber = '01', isp, route] = match;
    const vendorCode = vendors[vendor.toLowerCase()];
    const protocolCode = protocols[String(p.type).toLowerCase()];

    if (!vendorCode) throw new Error('请补充厂商编码：' + vendor);
    if (!protocolCode) throw new Error('请补充协议编码：' + p.type);

    const countryCode = country.toUpperCase();
    const name = [
      vendorCode + protocolCode,
      countryCode,
      region,
      lineNumber,
      (isp || route).trim()
    ].join(' ');

    const source = match[0] + ' [' + p.type + ']';
    if (seen.has(name)) {
      throw new Error('节点重名 ' + name + '：' + seen.get(name) + ' 和 ' + source);
    }

    seen.set(name, source);
    p.name = name;
  }

  return proxies;
}

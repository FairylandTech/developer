const vendorCodes = new Map([
  ['bwh', 'S01'],
  ['raksmart', 'S11'],
  ['fuidc', 'S12'],
  ['aws', 'S31'],
]);

const protocolCodes = new Map([
  ['vless', 'P01'],
  ['anytls', 'P02'],
]);

function parseTitle(title) {
  // 面板 服务提供商 国家 地区 [服务器编号] [NAT] 线路描述
  const header = /^\S+ (\S+) (\S+) (\S+)(?: (\d+))? (?:NAT )?(.+)$/i;
  const match = title.trim().replace(/\s+/g, ' ').match(header);
  if (!match) return null;

  const [, vendor, country, region, number = '01', route] = match;
  let suffix = '';

  if (!/^Native$/i.test(route)) {
    // 兼容旧标题中的 ISP(名称) 以及全角括号。
    const isp = /^ISP(?: ?[(（]([^()（）]+)[)）]| ([^()（）]+))$/i.exec(route);
    const transfer = /^Transfer ([^ ()（）]+) ([^()（）]+?)(?: *[(（]([^()（）]+)[)）])?$/i.exec(route);

    if (isp) {
      const label = (isp[1] || isp[2]).trim();
      if (!label) return null;
      suffix = ' ISP ' + label;
    } else if (transfer) {
      const [, destinationCountry, destinationRegion, provider] = transfer;
      if (provider && !provider.trim()) return null;
      suffix = ' -> ' + destinationCountry + ' ' + destinationRegion.trim();
      if (provider) suffix += ' (' + provider.trim() + ')';
    } else {
      return null;
    }
  }

  return { vendor, country: country.toUpperCase(), region, number: number.padStart(2, '0'), suffix };
}

function operator(proxies) {
  const seen = new Map();
  const renamed = proxies.map(proxy => {
    const titles = [proxy._subDisplayName, proxy._subName].filter(Boolean);
    let route;
    let source;
    for (const title of titles) {
      route = parseTitle(title);
      if (route) {
        source = title;
        break;
      }
    }
    if (!route) throw new Error('订阅标题格式不匹配：' + (titles.join(' / ') || '缺少来源名称'));

    const vendorCode = vendorCodes.get(route.vendor.toLowerCase());
    const protocolCode = protocolCodes.get(String(proxy.type).toLowerCase());
    if (!vendorCode) throw new Error('请补充厂商编码：' + route.vendor);
    if (!protocolCode) throw new Error('请补充协议编码：' + proxy.type);

    const prefix = [vendorCode + protocolCode, route.country, route.region, route.number].join(' ');
    const name = prefix + route.suffix;
    const origin = source + ' [' + proxy.type + ']';
    if (seen.has(name)) throw new Error('节点重名 ' + name + '：' + seen.get(name) + ' 和 ' + origin);
    seen.set(name, origin);
    return { ...proxy, name };
  });

  return renamed.sort((a, b) => a.name.localeCompare(b.name, 'en'));
}

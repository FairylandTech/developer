function main(config) {
  const groups = config['proxy-groups'];
  const proxyGroup = groups?.find(group => group.name === 'Proxy');
  if (!proxyGroup || !config.proxies?.length) {
    throw new Error('请先加载模板和订阅节点');
  }

  const nodeNames = config.proxies.map(proxy => proxy.name);
  const offlineTitle = 'AAA·搬砖专线直达洛杉矶';
  const offlineNames = new Set([offlineTitle, '✈️ ' + offlineTitle]);
  const selectableNodes = nodeNames.filter(name => !offlineNames.has(name));
  const replacedNames = new Set([...nodeNames, ...offlineNames]);

  proxyGroup.proxies = nodeNames;
  delete proxyGroup['include-all'];

  for (const group of groups) {
    if (group === proxyGroup || !group.proxies?.includes('Proxy')) continue;

    const entries = group.proxies.filter(name => !replacedNames.has(name));
    entries.splice(entries.indexOf('Proxy') + 1, 0, ...selectableNodes);
    group.proxies = entries;
    delete group['include-all'];
  }
  return config;
}

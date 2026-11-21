function main(config) {
  const group = config['proxy-groups'].find(g => g.name === 'Proxy');
  if (!group || !config.proxies?.length) {
    throw new Error('请先加载模板和订阅节点');
  }

  group.proxies = config.proxies.map(p => p.name);
  delete group['include-all'];
  return config;
}
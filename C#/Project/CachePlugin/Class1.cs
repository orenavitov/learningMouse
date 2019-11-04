using OSGi.NET.Core;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using LogPlugin;
using log4net;

namespace CachePlugin
{
    public class Class1 : IBundleActivator
    {
        private ILogService logService;

        public void Start(IBundleContext context)
        {
            var serviceReference = context.GetServiceReference<ILogService>();
            var logService = context.GetService<ILogService>(serviceReference);
            ILog log = logService.GetLogger(typeof(Class1));
            log.Debug("CachePlugin Started!");
        }

        public void Stop(IBundleContext context)
        {
            ILog log = logService.GetLogger(typeof(Class1));
            log.Debug("CachePlugin Stopped!");
        }
    }
}

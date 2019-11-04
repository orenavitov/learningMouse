using log4net;
using LogPlugin;
using OSGi.NET.Core;
using OSGi.NET.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBPlugin
{
    public class Class1 : IBundleActivator
    {
        private IServiceRegistration serviceRegistration;
        private ILogService logService;

        public void Start(IBundleContext context)
        {
            var serviceReference = context.GetServiceReference<ILogService>();
            var logService = context.GetService<ILogService>(serviceReference);
            ILog log = logService.GetLogger(typeof(Class1));
            //serviceRegistration = context.RegisterService<>
            log.Debug("DBPlugin Started!");
        }

        public void Stop(IBundleContext context)
        {
            ILog log = logService.GetLogger(typeof(Class1));
            log.Debug("DBPlugin Stopped!");
        }
    }
}

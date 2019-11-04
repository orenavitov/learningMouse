using log4net;
using OSGi.NET.Core;
using OSGi.NET.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LogPlugin
{
    public class Class1 : IBundleActivator
    {
        private IServiceRegistration serviceRegistration;
        private ILog log = LogManager.GetLogger(typeof(Class1));

        public void Start(IBundleContext context)
        {
            serviceRegistration = context.RegisterService<ILogService>(new LogService());
            log.Debug("LogPlugin Started!");
        }

        public void Stop(IBundleContext context)
        {
            log.Debug("LogPlugin Stopped!");
        }
    }
}

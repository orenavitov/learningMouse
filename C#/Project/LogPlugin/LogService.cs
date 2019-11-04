using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LogPlugin
{
    class LogService : ILogService
    {
        public ILog GetLogger(Type type)
        {
            return LogManager.GetLogger(type);            
        }


    }
}

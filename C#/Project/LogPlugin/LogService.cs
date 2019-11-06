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
        ILog log;

        public void Debug(string debugMessage)
        {
            
        }

        public void Error(string errorMessage)
        {
            
        }

        public ILog GetLogger(Type type)
        {

            return LogManager.GetLogger(type);            
        }

        public void Info(string infoMessage)
        {
            
        }

        public void Warn(string warnMessage)
        {
            
        }
    }
}

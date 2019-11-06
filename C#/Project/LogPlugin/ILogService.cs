using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LogPlugin
{
    public interface ILogService
    {
        ILog GetLogger(Type type);

        void Debug(string debugMessage);

        void Info(string infoMessage);

        void Error(string errorMessage);

        void Warn(string warnMessage);
    }

    
}

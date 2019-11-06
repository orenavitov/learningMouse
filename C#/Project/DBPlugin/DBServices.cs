using log4net;
using LogPlugin;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBPlugin
{
    class DBServices: IDBServices
    {
        private ILogService logService;
        private static string SqlServerConnString = "";

        private SqlConnection sqlConnection;

        private ILog log;

        public DBServices()
        {
            initDBServices();
            log = logService.GetLogger(typeof(DBServices));
        }

        public DBServices(ILogService logService)
        {
            this.logService = logService;
            
        }

        public bool createOperation(string sqlCommond)
        {
            return false;
        }

        public bool deleteOperation(string sqlCommond)
        {
            return false;
        }

        public bool executeCommands(string sqlCommand)
        {
            return false;
        }

        public void initDBServices(SqlConnection sqlConnection)
        {
            this.sqlConnection = sqlConnection;
        }

        public bool modifyOperation(string sqlCommond)
        {
            return false;
        }

        public object queryOperation(string sqlCommond)
        {
            return null;
        }

        public List<object> queryOperations(string sqlCommond)
        {
            return null;
        }

        public void initDBServices()
        {

        }

        
    }
}

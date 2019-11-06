using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBPlugin
{
    public interface IDBServices
    {
        void initDBServices();

        void initDBServices(SqlConnection sqlConnection);

        bool createOperation(string sqlCommond);

        bool deleteOperation(string sqlCommond);

        bool modifyOperation(string sqlCommond);

        object queryOperation(string sqlCommond);

        List<object> queryOperations(string sqlCommond);

        bool executeCommands(string sqlCommand);
    }
}

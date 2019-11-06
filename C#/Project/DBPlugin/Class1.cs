using log4net;
using LogPlugin;
using OSGi.NET.Core;
using OSGi.NET.Service;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBPlugin
{
    public class Class1 : IBundleActivator
    {
        private IServiceRegistration serviceRegistration;
        private ILogService logService;
        private ILog log;

        private static string SqlServerConnString = @"Data Source=127.0.0.1,1433;database=MihTest;uid=sa;pwd=123";
        private static string WindowsServerConnString = @"Data Source=DESKTOP-RKK5DDE\SQLEXPRESS;Initial Catalog=MihTest;Integrated Security=TRUE";
        private void checkDataBase()
        {
            try
            {
                using (SqlConnection sqlConn = new SqlConnection(SqlServerConnString))
                {
                    sqlConn.Open();
                    // 构建查询语句
                    string selectSql = "select * from Person";
                    SqlCommand command = new SqlCommand();
                    command.CommandType = System.Data.CommandType.Text;
                    command.CommandText = selectSql;

                    SqlDataAdapter sqlDataAdapter = new SqlDataAdapter(command);
                    sqlDataAdapter.SelectCommand.Connection = sqlConn;

                    DataTable dataTable = new DataTable();
                    sqlDataAdapter.Fill(dataTable);

                    foreach (DataRow dataRow in dataTable.Rows)
                    {
                        string name = Convert.ToString(dataRow["name"]);
                        log.Info("name: " + name);
                        int age = Convert.ToInt32(dataRow["age"]);
                        log.Info("age: " + age);


                    }

                    sqlConn.Close();
                }
            }
            catch (Exception ex)
            {
                log.Error(ex);
            }
        }

        public void Start(IBundleContext context)
        {
            // 获取服务
            var serviceReference = context.GetServiceReference<ILogService>();
            var logService = context.GetService<ILogService>(serviceReference);
            
            log = logService.GetLogger(typeof(Class1));

            // 注册服务
            //serviceRegistration = context.RegisterService<IDBServices>(new DBServices(logService));

            checkDataBase();

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

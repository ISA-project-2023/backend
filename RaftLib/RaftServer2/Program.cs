using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;
using RaftLib;

namespace RaftServer2
{
    class Program
    {
        static void Main(string[] args)
        {
            string serviceAddress = "127.0.0.1:4002";
            string serviceName = "RaftService2";

            ServiceHost host = new ServiceHost(typeof(Service2), new Uri($"net.tcp://{serviceAddress}/{serviceName}"));

            NetTcpBinding serverBinding = new NetTcpBinding();

            host.AddServiceEndpoint(typeof(IService2), serverBinding, "");
            host.Open();

            Console.WriteLine("Service 2 is running. Press Enter to stop.");
            Console.ReadLine();
        }
    }
}

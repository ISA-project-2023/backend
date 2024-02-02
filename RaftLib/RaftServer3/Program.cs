using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;
using RaftLib;

namespace RaftServer3
{
    class Program
    {
        static void Main(string[] args)
        {
            string serviceAddress = "127.0.0.1:4003";
            string serviceName = "RaftService3";

            ServiceHost host = new ServiceHost(typeof(Service3), new Uri($"net.tcp://{serviceAddress}/{serviceName}"));

            NetTcpBinding serverBinding = new NetTcpBinding();

            host.AddServiceEndpoint(typeof(IService3), serverBinding, "");
            host.Open();

            Console.WriteLine("Service 3 is running. Press Enter to stop.");
            Console.ReadLine();
        }
    }
}

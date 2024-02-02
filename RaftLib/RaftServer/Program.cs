using RaftLib;
using System;
using System.ServiceModel;

namespace RaftServer
{
    class Program
    {
        static void Main(string[] args)
        {
            string serviceAddress1 = "127.0.0.1:4001";
            string serviceName1 = "RaftService1";
            string serviceAddress2 = "127.0.0.1:4002";
            string serviceName2 = "RaftService2";
            string serviceAddress3 = "127.0.0.1:4003";
            string serviceName3 = "RaftService3";
            string[] addresses = { serviceAddress1, serviceAddress2, serviceAddress3 };
            string[] names= { serviceName1, serviceName2, serviceName3 };


            Service1 serviceInstance1 = new Service1(1, addresses, names);
            Service1 serviceInstance2 = new Service1(2, addresses, names);
            Service1 serviceInstance3 = new Service1(3, addresses, names);

            ServiceHost host1 = new ServiceHost(serviceInstance1, new Uri($"net.tcp://{serviceAddress1}/{serviceName1}"));
            ServiceHost host2 = new ServiceHost(serviceInstance2, new Uri($"net.tcp://{serviceAddress2}/{serviceName2}"));
            ServiceHost host3 = new ServiceHost(serviceInstance3, new Uri($"net.tcp://{serviceAddress3}/{serviceName3}"));

            NetTcpBinding serverBinding = new NetTcpBinding();

            host1.AddServiceEndpoint(typeof(IService1), serverBinding, "");
            host2.AddServiceEndpoint(typeof(IService1), serverBinding, "");
            host3.AddServiceEndpoint(typeof(IService1), serverBinding, "");

            host1.Open();
            host2.Open();
            host3.Open();

            Console.WriteLine("Service is running. Press Enter to stop.");
            Console.ReadLine();
        }
    }
}

using System;
using System.ServiceModel;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RaftLib;

namespace RaftClient
{
    class Program
    {
        static void Main(string[] args)
        {
            

            //Testing 
            Console.Write("Enter id number to shutdown the leader server or enter to close this window:");

            int number = int.Parse(Console.ReadLine());
            string serviceAddress;
            string serviceName;
            switch (number)
            {
                case 1:
                    serviceAddress = "127.0.0.1:4001";
                    serviceName = "RaftService1";
                    break;
                case 2:
                    serviceAddress = "127.0.0.1:4002";
                    serviceName = "RaftService2";
                    break;
                default:
                    serviceAddress = "127.0.0.1:4003";
                    serviceName = "RaftService3";
                    break;
            }
             

            Uri tcpUri = new Uri($"net.tcp://{serviceAddress}/{serviceName}");

            EndpointAddress adress = new EndpointAddress(tcpUri);

            NetTcpBinding clientBinding = new NetTcpBinding();

            ChannelFactory<IService1> factory = new ChannelFactory<IService1>(clientBinding, adress);

            IService1 service = factory.CreateChannel();
            service.ShutDownLeader();

            Console.ReadLine();
        }
    }
}

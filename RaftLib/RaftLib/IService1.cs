using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Timers;

namespace RaftLib
{
    [ServiceContract]
    
    public interface IService1
    {
        [OperationContract]
        void SendVoteRequest();
        [OperationContract]
        int SendVoteResponse(int Id);
        [OperationContract]
        void ProclaimAsTheLeader();
        [OperationContract]
        void SendHeartBeat();
        [OperationContract]
        void RecieveHeartBeat(int id);
        [OperationContract]
        bool RecieveLeader(int id);
        [OperationContract]
        void ShutDownLeader();
        [OperationContract]
        void RestartTimer();
    }

}

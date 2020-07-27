using System;
using System.Threading.Tasks;
using System.Text.Json;

/* The application logic is meant for demonstration purposes only;
 * this app sends a request for an exchange rate every 10 seconds
 * to the message broker and awaits a response. 
 */

namespace DemoApp
{
    public class DemoApp
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("(A.0) Demo app initiated");
            string fromCCY = args.Length > 0 ? args[0] : "USD";
            string toCCY = args.Length > 0 ? args[0] : "EUR";

            RpcRequest request = new RpcRequest
            {
                fromCCY = fromCCY,
                toCCY = toCCY
            };
            Console.WriteLine("(A.1) FX request USD->EUR prepared, starting loop");

            var counter = 0;
            var max = args.Length != 0 ? Convert.ToInt32(args[0]) : -1;
            while (max == -1 || counter < max)
            {        
                System.Threading.Thread.Sleep(10000);
                Console.WriteLine($"(A.2) Initiating request #{++counter}");
                RpcResponse rpcResponse = Task.Run(async () => await GetRpcResult(request)).Result;
                Console.WriteLine($"(A.7) Response: {rpcResponse.fromCCY}->{rpcResponse.toCCY}:{rpcResponse.exchangeRate}");
            }
        }

        private static async Task<RpcResponse> GetRpcResult(RpcRequest request)
        {
            Console.WriteLine("(A.3) Async request task started");
            string jsonRequest = JsonSerializer.Serialize(request);
            Console.WriteLine("(A.4) Request stringified");
            var rpcClient = new RpcClient();
            Console.WriteLine("(A.5) RpcClient created");
            var response = await rpcClient.CallAsync(jsonRequest);
            Console.WriteLine("(A.6) Response received");
            rpcClient.Close();

            RpcResponse rpcResponse = JsonSerializer.Deserialize<RpcResponse>(response);

            return rpcResponse;
        }
    }
}

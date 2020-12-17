using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using Wienerberger.WebService.Services.Contracts;
using Wienerberger.WebService.Services.Model;

namespace Wienerberger.WebService.Services.Services
{
    public class FascadeService : IFascadeService
    {
        private readonly string BaseUrl;

        public FascadeService()
        {
            BaseUrl = "https://foiwbstorage.blob.core.windows.net/foi-wb/wb-products/wb-products";
        }

        public async Task<List<Fascade>> GetAll()
        {
            List<int> productIds = new List<int> { 857435, 857441, 857444, 857447, 857450, 857457, 857460, 857462, 857469, 857473, 857476, 857482 };
            List<Fascade> fascades = new List<Fascade>();
            using (var httpClient = new HttpClient())
            {
                foreach (var productId in productIds)
                {
                    var a = this.BaseUrl + $"facade/{productId}.infinity.json";
                    using (var response = await httpClient.GetAsync(this.BaseUrl + $"/facade/{productId}.infinity.json"))
                    {
                        string apiResponse = await response.Content.ReadAsStringAsync();
                        var fascade = JsonConvert.DeserializeObject<Fascade>(apiResponse);
                        fascades.Add(fascade);
                    }
                }
                fascades = fascades.Select(f => { f.Assets = this.BaseUrl + f.Assets["asset0"]["fileReference"].ToString().Replace("{","").Replace("}","");  return f; }).ToList();
                return fascades;
            }
        }
    }
}

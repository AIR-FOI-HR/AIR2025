using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using Wienerberger.WebService.Services.Model;

namespace Wienerberger.WebService.Services.Contracts
{
    public interface IFascadeService
    {
        Task<List<Fascade>> GetAll();
    }
}

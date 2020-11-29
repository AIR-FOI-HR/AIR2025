using AutoMapper;
using DC = Wienerberger.WebService.API.DataContracts;
using S = Wienerberger.WebService.Services.Model;

namespace Wienerberger.WebService.IoC.Configuration.AutoMapper.Profiles
{
    public class APIMappingProfile : Profile
    {
        public APIMappingProfile()
        {
            //CreateMap<DC.User, S.User>().ReverseMap();
            //CreateMap<DC.Address, S.Address>().ReverseMap();
        }
    }
}

using System;
using System.Collections.Generic;
using System.Text;

namespace Wienerberger.WebService.Services.Model
{
    public class Fascade
    {
        public int PrimaId { get; set; }
        public string ProductName { get; set; }
        public dynamic Assets { get; set; }
        public string Teaser { get; set; }
        public string ProductBrand { get; set; }
    }
}

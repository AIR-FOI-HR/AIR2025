using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Drawing;

namespace Wienerberger.WebService.Services.Contracts
{
    public interface IImageService
    {
        bool TryConvertFromBase64(string base64String, ref Image convertedImage);
        bool IsBase64String(string base64);
    }
}

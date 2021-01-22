using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Text;
using Wienerberger.WebService.Services.Contracts;

namespace Wienerberger.WebService.Services.Services
{
    public class ImageService : IImageService
    {
        public bool TryConvertFromBase64(string base64String, ref Image convertedImage)
        {
            base64String = base64String.Replace("data:image/jpeg;base64,", "");
            bool isValid = IsBase64String(base64String);

            if(isValid)
            {
                // Convert Base64 String to byte[]
                byte[] imageBytes = Convert.FromBase64String(base64String);
                MemoryStream ms = new MemoryStream(imageBytes, 0, imageBytes.Length);

                // Convert byte[] to Image
                ms.Write(imageBytes, 0, imageBytes.Length);
                convertedImage = Image.FromStream(ms, true);

                return true;
            }
            else
            {
                return false;
            }
        }

        /// <summary>
        /// Check if string is Base64
        /// </summary>
        /// <param name="base64"></param>
        /// <returns></returns>
        public bool IsBase64String(string base64)
        {
            Span<byte> buffer = new Span<byte>(new byte[base64.Length]);
            return Convert.TryFromBase64String(base64, buffer, out int _);
        }
    }
}

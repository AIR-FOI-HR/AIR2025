using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Wienerberger.WebService.API.DataContracts.Requests;
using Wienerberger.WebService.Services.Contracts;
using System;
using System.Threading.Tasks;
using System.Collections.Generic;
using Wienerberger.WebService.Services.Model;
using System.Drawing;

namespace Wienerberger.WebService.API.Controllers.V1
{
    [ApiVersion("1.0")]
    [Route("api/Products")]
    [ApiController]
    public class ProductsController : Controller
    {
        private readonly IImageService _imageService;
        private readonly IMapper _mapper;
        private readonly ILogger<ProductsController> _logger;

        public ProductsController(IImageService imageService, IMapper mapper, ILogger<ProductsController> logger)
        {
            _imageService = imageService;
            _mapper = mapper;
            _logger = logger;
        }

        #region GET
        /// <summary>
        /// Returns a list of Wienerberger that most closely resemble bricks on recieved picture.
        /// </summary>
        /// <remarks>
        /// </remarks>
        /// <returns>
        /// Returns a list of Wienerberger that most closely resemble bricks on recieved picture.
        /// </returns>
        /// <response code="200">Returns list of suggested products</response>
        /// <response code="400">If passed base64 string is empty</response>
        /// <response code="422">If passed base64 string can't be converted to the image</response>
        [ProducesResponseType(StatusCodes.Status200OK, Type = typeof(List<Product>))]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status422UnprocessableEntity)]
        [HttpPost]
        public async Task<IActionResult> GetProductRecommendations([FromBody] GetProductRequest getProductsRequest)
        {
            _logger.LogDebug($"ProductsControllers::POST::{getProductsRequest.Base64String}");

            if(String.IsNullOrEmpty(getProductsRequest.Base64String))
            {
                return BadRequest(getProductsRequest);
            }

            Image convertedImage = null;
            var successfullConversion = _imageService.TryConvertFromBase64(getProductsRequest.Base64String, ref convertedImage);

            if(!successfullConversion)
            {
                return UnprocessableEntity(getProductsRequest);
            }

            return Ok();
        }
        #endregion
    }
}

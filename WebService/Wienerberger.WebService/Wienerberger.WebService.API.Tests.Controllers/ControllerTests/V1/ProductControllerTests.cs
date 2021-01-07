using AutoMapper;
using Wienerberger.WebService.API.Controllers.V1;
using Wienerberger.WebService.API.DataContracts.Requests;
using Wienerberger.WebService.API.DataContracts;
using Wienerberger.WebService.Services.Contracts;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

namespace Wienerberger.WebService.API.Tests.Controllers.ControllerTests.V1
{
    [TestClass]
    public class ProductControllerTests : TestBase
    {
        //NOTE: should be replaced by an interface
        ProductsController _controller;

        public ProductControllerTests() : base()
        {
            var imageService = _serviceProvider.GetRequiredService<IImageService>();
            var mapper = _serviceProvider.GetRequiredService<IMapper>();
            var loggerFactory = _serviceProvider.GetRequiredService<ILoggerFactory>();
            var logger = loggerFactory.CreateLogger<ProductsController>();

            _controller = new ProductsController(imageService, mapper, logger);
        }

        [TestMethod]
        public async Task Post_EmptyBase64Property_ReturnsBadRequest()
        {
            var products = await _controller.GetProductRecommendations(new GetProductRequest());

            Assert.IsInstanceOfType(products, typeof(BadRequestObjectResult));
        }

        [TestMethod]
        public async Task Post_InvalidBase64String_ReturnsUnprocessableEntity()
        {
            var products = await _controller.GetProductRecommendations(new GetProductRequest
            {
                Base64String = "InvalidString"
            });

            Assert.IsInstanceOfType(products, typeof(UnprocessableEntityObjectResult));
        }


    }
}

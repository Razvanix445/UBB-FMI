using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading;
using System.Threading.Tasks;
using Newtonsoft.Json;
using TransportModel.transport.model;

namespace ProiectCSharp
{
	class MainClass
	{
		//static HttpClient client = new HttpClient();
		//pentru jurnalizarea operatiilor efectuate si a datelor trimise/primite
		static HttpClient client = new HttpClient(new LoggingHandler(new HttpClientHandler()));

		private static string URL_Base = "http://localhost:8080/transport/trips";

		public static void Main(string[] args)
		{
			//Console.WriteLine("Hello World!");
			RunAsync().Wait();
		}


		static async Task RunAsync()
		{
			client.BaseAddress = new Uri("http://localhost:8080/transport/greeting");
			client.DefaultRequestHeaders.Accept.Clear();
			//client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("text/plain"));
			client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
			// Get the string
			String text = await GetTextAsync("http://localhost:8080/transport/trips/greeting");
			Console.WriteLine("am obtinut {0}", text);
			
			// FindOne Functionality
			Console.WriteLine("Find trip with id 32");
			TripDTO foundTripDTO = await GetTripAsync($"http://localhost:8080/transport/trips/32");
			if (foundTripDTO != null)
			{
				Trip foundTrip = new Trip
				{
					id = long.Parse(foundTripDTO.Id),
					destination = foundTripDTO.Destination,
					dateDeparture = DateTime.Parse(foundTripDTO.DateDeparture),
					noOfAvailableSeats = long.Parse(foundTripDTO.NoOfAvailableSeats)
				};
				Console.WriteLine("Found trip: {0}", foundTrip);
			}
			else
				Console.WriteLine("Trip not found");
			Console.ReadLine();

			// FindAll Functionality
			Console.WriteLine("Find all trips");
			List<TripDTO> allTripsDTO = await GetAllTripsAsync("http://localhost:8080/transport/trips");
			foreach (var tripDTO in allTripsDTO)
			{
				Trip tripToFind = new Trip
				{
					id = long.Parse(tripDTO.Id),
					destination = tripDTO.Destination,
					dateDeparture = DateTime.Parse(tripDTO.DateDeparture),
					noOfAvailableSeats = long.Parse(tripDTO.NoOfAvailableSeats)
				};
				Console.WriteLine("Found trip: {0}", tripToFind);
			}
			Console.ReadLine();
			
			// Add Functionality
			DateTime date = DateTime.Now;
			string formattedDate = date.ToString("yyyy-MM-ddTHH:mm:ss");
			TripDTO trip = new TripDTO{Destination = "destinationcsharp", DateDeparture = formattedDate, NoOfAvailableSeats = "6"};
			Console.WriteLine("Create user {0}", trip);
			Trip result = await CreateTripAsync("http://localhost:8080/transport/trips", trip);
			Console.WriteLine("Am primit {0}", result);
			Console.ReadLine();
			
			// Delete Functionality
			HttpResponseMessage response = await client.DeleteAsync($"http://localhost:8080/transport/trips/{result.id}");
			if (response.IsSuccessStatusCode)
			{
				TripDTO deletedTripDTO = await response.Content.ReadAsAsync<TripDTO>();
				Trip deletedTrip = new Trip
				{
					id = long.Parse(deletedTripDTO.Id),
					destination = deletedTripDTO.Destination,
					dateDeparture = DateTime.Parse(deletedTripDTO.DateDeparture),
					noOfAvailableSeats = long.Parse(deletedTripDTO.NoOfAvailableSeats)
				};
				Console.WriteLine("Deleted trip: {0}", deletedTrip);
			} else 
				Console.WriteLine("Error: {0}", response.StatusCode);
			Console.ReadLine();
			
			// Update Functionality
			DateTime updatedDate = DateTime.Now;
			string updatedFormattedDate = updatedDate.ToString("yyyy-MM-ddTHH:mm:ss");
			TripDTO updatedTripDTO = new TripDTO{Id = "32", Destination = "updatedDestination", DateDeparture = updatedFormattedDate, NoOfAvailableSeats = "7"};
			Console.WriteLine("Update trip {0}", updatedTripDTO);
			Trip updatedResult = await UpdateTripAsync($"http://localhost:8080/transport/trips/32", updatedTripDTO);
			Console.WriteLine("Updated trip: {0}", updatedResult);
			Console.ReadLine();
		}
		
		static async Task<TripDTO> GetTripAsync(string path)
		{
			TripDTO trip = null;
			HttpResponseMessage response = await client.GetAsync(path);
			if (response.IsSuccessStatusCode)
			{
				trip = await response.Content.ReadAsAsync<TripDTO>();
			}
			return trip;
		}

		static async Task<List<TripDTO>> GetAllTripsAsync(string path)
		{
			List<TripDTO> trips = null;
			HttpResponseMessage response = await client.GetAsync(path);
			if (response.IsSuccessStatusCode)
			{
				trips = await response.Content.ReadAsAsync<List<TripDTO>>();
			}
			return trips;
		}

		static async Task<String> GetTextAsync(string path)
		{
			String product = null;
			HttpResponseMessage response = await client.GetAsync(path);
			if (response.IsSuccessStatusCode)
			{
				product = await response.Content.ReadAsStringAsync();
			}
			return product;
		}
		
		static async Task<Trip> CreateTripAsync(string path, TripDTO trip)
		{
			Trip result = null;
			HttpResponseMessage response = await client.PostAsJsonAsync(path, trip);
			if (response.IsSuccessStatusCode)
			{
				TripDTO receivedTripDTO = await response.Content.ReadAsAsync<TripDTO>();
				result = new Trip
				{
					id = long.Parse(receivedTripDTO.Id),
					destination = receivedTripDTO.Destination,
					dateDeparture = DateTime.Parse(receivedTripDTO.DateDeparture),
					noOfAvailableSeats = long.Parse(receivedTripDTO.NoOfAvailableSeats)
				};
			}
			return result;
		}
		
		static async Task<Trip> UpdateTripAsync(string path, TripDTO trip)
		{
			Trip result = null;
			HttpResponseMessage response = await client.PutAsJsonAsync(path, trip);
			if (response.IsSuccessStatusCode)
			{
				TripDTO receivedTripDTO = await response.Content.ReadAsAsync<TripDTO>();
				result = new Trip
				{
					id = long.Parse(receivedTripDTO.Id),
					destination = receivedTripDTO.Destination,
					dateDeparture = DateTime.Parse(receivedTripDTO.DateDeparture),
					noOfAvailableSeats = long.Parse(receivedTripDTO.NoOfAvailableSeats)
				};
			}
			return result;
		}
	}
	
	public class TripDTO
	{
		[JsonProperty("id")]
		public string Id { get; set; }
		[JsonProperty("destination")]
		public string Destination { get; set; }
		[JsonProperty("dateDeparture")]
		public string DateDeparture { get; set; }
		[JsonProperty("noOfAvailableSeats")]
		public string NoOfAvailableSeats { get; set; }

		public override string ToString()
		{
			return string.Format("[Trip: Id={0}, Destination={1}, DateDeparture={2}, NoOfAvailableSeats={3}]", Id, Destination, DateDeparture, NoOfAvailableSeats);
		}
	}
	
	public class LoggingHandler : DelegatingHandler
    {
        public LoggingHandler(HttpMessageHandler innerHandler)
            : base(innerHandler)
        {
        }
    
        protected override async Task<HttpResponseMessage> SendAsync(HttpRequestMessage request, CancellationToken cancellationToken)
        {
            Console.WriteLine("Request: ");
            Console.WriteLine(request.ToString());
            if (request.Content != null)
            {
                Console.WriteLine(await request.Content.ReadAsStringAsync());
            }
            Console.WriteLine();
    
            HttpResponseMessage response = await base.SendAsync(request, cancellationToken);
    
            Console.WriteLine("Response: ");
            Console.WriteLine(response.ToString());
            if (response.Content != null)
            {
                Console.WriteLine(await response.Content.ReadAsStringAsync());
            }
            Console.WriteLine();
    
            return response;
        }
    }
}

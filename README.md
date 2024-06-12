## Spring REST API - PictureHaven

This project implements a Spring Boot REST API for managing pictures, artists, tags, and their associations in an application called "PictureHaven".
PictureHaven is a platform where users can upload pictures, tag them, and view pictures by artists or tags.
Pictures are analysed to extract their dominant colors, which can be used to style HTML elements associated with the pictures at the front end.

The name PictureHaven is inspired by the idea of a safe haven for pictures. The platform is intended to be a place where users can store and manage their pictures. A "Picture" refers to a painting, a photograph, or any other form of visual art that is represented by a digital image.

The idea came about while building a gallery style website using React. It occurred to me that it could be interesting to dynamically style the website based on the dominant colours of its images. This project is a backend implementation of that idea.

#### _Coming soon:_
Artists will be able to create Collections of pictures and tag them with a specific theme. This would allow users to view pictures by collection and theme.
The HTML elements associated with a Collection could be styled based on the collectively dominant colors of the pictures in the collection. The idea would be to capture the color palette of the collection as a whole.

### Features
* Manage Pictures:
  * Create, retrieve, update, and delete pictures.
  * Get a list of all pictures.
  * Retrieve a picture by its ID.
  * Get the dominant colors of a picture.
* Manage Artists:
    * Create, retrieve, update, and delete artists.
    * Get a list of all artists.
    * Retrieve an artist by their ID.
* Manage Tags:
  * Create, retrieve, and delete tags.
  * Get a list of all tags.
  * Retrieve a tag by its ID.
* Manage Picture-Tag Associations:
  * Get pictures associated with a specific tag.
  * Add a tag to a picture (implemented in PictureController).

### Technologies Used
* Spring Boot 3.3.0
* Spring Data JDBC (for relational database access)
* PostgreSQL (database)

### Running the application
1. Prerequisites:
    * Java 17 or later
    * Maven (build tool)
    * PostgreSQL (database)
   
2. Configuration:
   * Update the spring.datasource.username and spring.datasource.password properties in application.properties with your PostgreSQL credentials.
   
3. Build and Run:
   * Clone this repository.
   * Open a terminal in the project directory.
   * Run `mvn clean install` to build the project.
   * Run the application using `mvn spring-boot:run`.

4. API Access:
   * The application usually runs on port 8080 by default (configure a different port in application.properties).
   * Use tools like Postman or curl to interact with the API endpoints.

### API Endpoints

**Pictures:**

* GET /api/pictures: Retrieves a list of all pictures.
* GET /api/pictures/{id}: Retrieves a picture by its ID.
* POST /api/pictures: Creates a new picture.
* PUT /api/pictures/{id}: Updates an existing picture.
* DELETE /api/pictures/{id}: Deletes a picture.
* GET /api/pictures/count: Returns the total number of pictures.
* GET /api/pictures/colors/{id}: Analyzes the image file associated with a picture and returns its dominant colors.

**Artists:**

* GET /api/artists: Retrieves a list of all artists.
* GET /api/artists/{id}: Retrieves an artist by their ID.
* POST /api/artists: Creates a new artist.
* PUT /api/artists/{id}: Updates an existing artist.
* DELETE /api/artists/{id}: Deletes an artist.
* GET /api/artists/count: Returns the total number of artists.

**Tags:**

* GET /api/tags: Retrieves a list of all tags.
* GET /api/tags/{id}: Retrieves a tag by its ID.
* POST /api/tags: Creates a new tag.
* DELETE /api/tags/{id}: Deletes a tag.
* GET /api/tags/count: Returns the total number of tags.

**Picture-Tag Associations:**

* GET /api/tags/pictures/{id}: Get pictures associated with a specific tag.
* POST /api/pictures/{pictureId}/tags/{tagId}: Add a tag to a picture.

### Extracting Dominant Colors
The /api/pictures/colors/{id} endpoint communicates with a feature to analyse an image file associated with a picture and return its most dominant colors. 
This functionality is implemented in the PictureService class.

**The service utilises a basic algorithm to achieve this:**
1. Looping through Pixels: It iterates through each pixel of the image using a nested loop and keeps track of the color occurrences in a HashMap.
2. Prioritizing Colors: It prioritizes the colours by their frequency using a PriorityQueue.
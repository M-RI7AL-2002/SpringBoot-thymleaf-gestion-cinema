# Spring Boot Configuration for Cinema Management

## Static Resources Configuration

Add this to your `application.properties`:

```properties
# Static resources
spring.web.resources.static-locations=classpath:/static/
spring.web.resources.cache.cachecontrol.max-age=31536000

# Thymeleaf configuration
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.cache=false
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.mode=HTML
```

## Directory Structure

Your Spring Boot project should have this structure:

```
src/main/resources/
├── static/
│   ├── bootstrap/
│   ├── css/
│   ├── fontawesome/
│   ├── images/
│   ├── js/
│   ├── magnific-popup/
│   ├── slick/
│   ├── waypoints/
│   └── animate.css/
└── templates/
    ├── layout.html
    ├── dashboard.html
    ├── films.html
    ├── seances.html
    └── billets.html
```

## Static Resources Included

✅ **Bootstrap** - CSS framework and JavaScript components
✅ **FontAwesome** - Icon library with all web fonts
✅ **Custom CSS** - Theme styles and dot-icons
✅ **jQuery** - JavaScript library
✅ **Custom JS** - Main script file
✅ **Images** - Logo parts, patterns, and markers
✅ **Magnific Popup** - Lightbox functionality
✅ **Slick Carousel** - Image/content carousel
✅ **Waypoints** - Scroll-triggered animations
✅ **Animate.css** - CSS animations

## Usage

All static resources are now properly organized in the `static/` folder and the templates have been updated to use the correct paths with Thymeleaf's `@{/static/...}` syntax.

The templates will automatically find and load all necessary CSS, JavaScript, and image files when you run your Spring Boot application.

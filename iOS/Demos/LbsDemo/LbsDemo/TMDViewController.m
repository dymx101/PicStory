//
//  TMDViewController.m
//  LbsDemo
//
//  Created by Yim Daniel on 13-2-26.
//  Copyright (c) 2013年 Yim Daniel. All rights reserved.
//

#import "TMDViewController.h"
#import "MyAnnotation.h"
#import <QuartzCore/QuartzCore.h>

@interface TMDViewController ()
@property (nonatomic, strong) MKMapView *myMapView;
@end

@implementation TMDViewController


- (void)viewDidLoad
{
    [super viewDidLoad];
	
    self.myMapView = [[MKMapView alloc] initWithFrame:self.view.bounds];
    // set the map type to satellite
    self.myMapView.mapType = MKMapTypeStandard;
    self.myMapView.delegate = self;
    self.myMapView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
    // add it to our view
    [self.view addSubview:self.myMapView];
    
    // 添加标记
    // this is just a sample location
    CLLocationCoordinate2D coordinate = CLLocationCoordinate2DMake(50.821, -0.138);
    // create the annotation using the location
    MyAnnotation *annotation = [[MyAnnotation alloc] initWithCoordinates:coordinate title:@"My Title" subTitle:@"My Sub Title"];
    annotation.pinColor = MKPinAnnotationColorPurple;
    // and eventually add it to the map
    [self.myMapView addAnnotation:annotation];
    
    // 定位
    if ([CLLocationManager locationServicesEnabled]) {
        self.myLocationManager = [[CLLocationManager alloc] init];
        self.myLocationManager.delegate = self;
        self.myLocationManager.purpose = @"to provide functionality on user's current location";
        [self.myLocationManager startUpdatingLocation];
    }
    else
    {
        // location services are not enabled. take appropriate action:for instance, prompt the user to enable the location services
        NSLog(@"location services are not enabled");
    }
    
    
    self.myGeocoder = [[CLGeocoder alloc] init];
    
    // 反向地址解析
    CLLocation *location = [[CLLocation alloc] initWithLatitude:+38.411f longitude:-122.840f];
    
    [self.myGeocoder reverseGeocodeLocation:location completionHandler:^(NSArray *placemarks, NSError *error) {
        if (error == nil && placemarks.count > 0) {
            CLPlacemark *placemark = [placemarks objectAtIndex:0];
            // we recieved the result
            NSLog(@"country = %@", placemark.country);
            NSLog(@"post code = %@", placemark.postalCode);
            NSLog(@"locality = %@", placemark.locality);
        }
        else if (error == nil && placemarks.count == 0)
        {
            NSLog(@"no result were returned.");
        }
        else if (error)
        {
            NSLog(@"an error occurred = %@", error);
        }
    }];
    
    // 正向地址解析
    NSString *oreillyAddress = @"1005 Gravenstein Highway North, Sebastopol, CA 95472, USA";
    [self.myGeocoder geocodeAddressString:oreillyAddress completionHandler:^(NSArray *placemarks, NSError *error) {
        if (placemarks.count > 0 && error == nil) {
            NSLog(@"found %lu placemark(s).", (unsigned long)placemarks.count);
            CLPlacemark *firstPlacemark = placemarks[0];
            NSLog(@"longitude = %f", firstPlacemark.location.coordinate.longitude);
            NSLog(@"latitude = %f", firstPlacemark.location.coordinate.latitude);
        }
        else if (placemarks.count == 0 && error == nil)
        {
            NSLog(@"found not placemarks.");
        }
        else if (error)
        {
            NSLog(@"an error occourred = %@", error);
        }
    }];
}

-(void)viewDidUnload
{
    [super viewDidUnload];
    
    [self.myLocationManager stopUpdatingLocation];
    self.myLocationManager = nil;
    self.myMapView = nil;
    self.myGeocoder = nil;
}


#pragma mark - core location manager delegate
-(void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation
{
    // we recieved the new location
    NSLog(@"Latitude = %f", newLocation.coordinate.latitude);
    NSLog(@"Longitude = %f", newLocation.coordinate.longitude);
}

-(void)locationManager:(CLLocationManager *)manager didFailWithError:(NSError *)error
{
    // failed to receive user's location
}

#pragma mark - map view delegate
-(MKAnnotationView *)mapView:(MKMapView *)mapView viewForAnnotation:(id<MKAnnotation>)annotation
{
    MKAnnotationView *result = nil;
    if (![annotation isKindOfClass:[MyAnnotation class]]) {
        return result;
    }
    
    if (![mapView isEqual:self.myMapView]) {
        // we want to process this event only for the map view that we have created previously
        return result;
    }
    
    // first typecast the annotation for which the map view has fired this delegate message
    MyAnnotation *senderAnnotation = (MyAnnotation *)annotation;
    
    // using the class method we have defined in our custom annotation class, we will attempt to get a reusable identifier for the pin are about to create
    NSString *pinReusableIdentifier = [MyAnnotation reusableIdentiferforPinColor:senderAnnotation.pinColor];
    
    // using the identifier we retrieved above, we will attempt to reuse a pin in the sender map view
    MKPinAnnotationView *annotationView = (MKPinAnnotationView *)[mapView dequeueReusableAnnotationViewWithIdentifier:pinReusableIdentifier];
    
    if (annotationView == nil) {
        // if we fail to reuse a pin, then we will create one
        annotationView = [[MKPinAnnotationView alloc] initWithAnnotation:senderAnnotation reuseIdentifier:pinReusableIdentifier];
        
        // make sure we can see the callouts on top of each pin in case we have assigned title and/or subtitle to each pin
        [annotationView setCanShowCallout:YES];
        
        // add custom view
//        UIView *customView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 50, 50)];
//        customView.layer.cornerRadius = 10;
//        customView.layer.backgroundColor = [UIColor lightGrayColor].CGColor;
//        [annotationView addSubview:customView];
    }
    
    
    
    // now make sure, whether we have reused a pin or not, that the color of the pin matches the color of pin matches the color of the annotation
    annotationView.pinColor = senderAnnotation.pinColor;
    
    UIImage *pinImage = [UIImage imageNamed:@"pin.png"];
    if (pinImage) {
        annotationView.image = pinImage;
    }
    
    result = annotationView;
    return result;
}

@end

//
//  GGGps.m
//  policeOnline
//
//  Created by dong yiming on 13-4-28.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGGps.h"


@implementation GGGps
{
    CLLocationManager *_locationManager;
    
}
DEF_SINGLETON(GGGps)

- (id)init
{
    self = [super init];
    if (self) {
        _locationManager = [[CLLocationManager alloc] init];
        _locationManager.delegate = self;
    }
    return self;
}

-(void)startUpdate
{
    _locationManager.delegate = self;
    [_locationManager startUpdatingLocation];
}

-(void)stopUpdate
{
    [_locationManager stopUpdatingLocation];
    _locationManager.delegate = nil;
}

#pragma mark - delegate
- (void)locationManager:(CLLocationManager *)manager
didUpdateToLocation:(CLLocation *)newLocation
fromLocation:(CLLocation *)oldLocation
{
    _location = newLocation;
    [_delegate gps:self
      gotLongitude:_location.coordinate.longitude
          latitude:_location.coordinate.latitude];
}

- (void)locationManager:(CLLocationManager *)manager
       didFailWithError:(NSError *)error
{
    [self stopUpdate];
}

@end

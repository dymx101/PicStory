//
//  MapviewDemoViewController.h
//  BaiduMapApiDemo
//
//  Copyright 2011 Baidu Inc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BMapKit.h"

@interface MapviewDemoViewController : UIViewController {
	BMKMapView* mapView;
}

@property (nonatomic, retain) IBOutlet BMKMapView* mapView;

@end

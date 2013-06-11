//
//  RouteSearchDemoViewController.h
//  BaiduMapApiDemo
//
//  Copyright 2011 Baidu Inc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BMapKit.h"

@interface RouteSearchDemoViewController : UIViewController<BMKMapViewDelegate, BMKSearchDelegate> {
	IBOutlet BMKMapView* _mapView;
	IBOutlet UITextField* _startCityText;
	IBOutlet UITextField* _startAddrText;
	IBOutlet UITextField* _startCoordainateXText;
	IBOutlet UITextField* _startCoordainateYText;
	IBOutlet UITextField* _endCityText;
	IBOutlet UITextField* _endAddrText;
	IBOutlet UITextField* _endCoordainateXText;
	IBOutlet UITextField* _endCoordainateYText;
	BMKSearch* _search;
}

-(IBAction)onClickBusSearch;
-(IBAction)onClickDriveSearch;
-(IBAction)onClickWalkSearch;

@end

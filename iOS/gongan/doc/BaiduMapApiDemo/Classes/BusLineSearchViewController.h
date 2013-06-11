//
//  BusLineSearchViewController.h
//  BaiduMapApiDemoSrc
//
//  Created by baidu on 12-6-20.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BMapKit.h"
@interface BusLineSearchViewController : UIViewController<BMKMapViewDelegate, BMKSearchDelegate> {
	IBOutlet BMKMapView* _mapView;
	IBOutlet UITextField* _cityText;
	IBOutlet UITextField* _busLineText;
	
	BMKSearch* _search;
    BMKPointAnnotation* _annotation;
}

-(IBAction)onClickBusLineSearch;

- (void)mapView:(BMKMapView *)mapView regionDidChangeAnimated:(BOOL)animated;

@end

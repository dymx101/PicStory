//
//  PoiSearchDemoViewController.m
//  BaiduMapApiDemo
//
//  Copyright 2011 Baidu Inc. All rights reserved.
//

#import "PoiSearchDemoViewController.h"

@implementation PoiSearchDemoViewController



// The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
/*
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization.
    }
    return self;
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	_mapView.delegate = self;
	_search = [[BMKSearch alloc]init];

	_cityText.text = @"北京";
	_keyText.text  = @"餐馆";
}

- (void)viewWillAppear:(BOOL)animated
{
    _search.delegate = self;
}



/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations.
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc. that aren't in use.
}

- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


- (void)dealloc {
    [super dealloc];
}


- (BMKAnnotationView *)mapView:(BMKMapView *)view viewForAnnotation:(id <BMKAnnotation>)annotation
{	
	static NSString *AnnotationViewID = @"annotationViewID";
	
    BMKAnnotationView *annotationView = [view dequeueReusableAnnotationViewWithIdentifier:AnnotationViewID];
    if (annotationView == nil) {
        annotationView = [[[BMKPinAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:AnnotationViewID] autorelease];
		((BMKPinAnnotationView*)annotationView).pinColor = BMKPinAnnotationColorRed;
		((BMKPinAnnotationView*)annotationView).animatesDrop = YES;
    }
	
	annotationView.centerOffset = CGPointMake(0, -(annotationView.frame.size.height * 0.5));
    annotationView.annotation = annotation;
	annotationView.canShowCallout = TRUE;
    return annotationView;
}

- (void)onGetTransitRouteResult:(BMKPlanResult*)result errorCode:(int)error
{
}

- (void)onGetDrivingRouteResult:(BMKPlanResult*)result errorCode:(int)error
{
}

- (void)onGetWalkingRouteResult:(BMKPlanResult*)result errorCode:(int)error
{
}

- (void)onGetPoiResult:(NSArray*)poiResultList searchType:(int)type errorCode:(int)error
{
	if (error == BMKErrorOk) {
		BMKPoiResult* result = [poiResultList objectAtIndex:0];
		for (int i = 0; i < result.poiInfoList.count; i++) {
			BMKPoiInfo* poi = [result.poiInfoList objectAtIndex:i];
			BMKPointAnnotation* item = [[BMKPointAnnotation alloc]init];
			item.coordinate = poi.pt;
			item.title = poi.name;
			[_mapView addAnnotation:item];
			[item release];
		}
	}
}

- (void)onGetAddrResult:(BMKAddrInfo*)result errorCode:(int)error
{
}

-(IBAction)onClickOk
{
	NSArray* array = [NSArray arrayWithArray:_mapView.annotations];
	[_mapView removeAnnotations:array];
	array = [NSArray arrayWithArray:_mapView.overlays];
	[_mapView removeOverlays:array];
	BOOL flag = [_search poiSearchInCity:_cityText.text withKey:_keyText.text pageIndex:0];
	if (!flag) {
		NSLog(@"search failed!");
	}
}
@end

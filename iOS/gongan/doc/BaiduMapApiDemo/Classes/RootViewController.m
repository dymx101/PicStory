//
//  RootViewController.m
//  BaiduMapApiDemo
//
//  Copyright 2011 Baidu Inc. All rights reserved.
//

#import "RootViewController.h"
#import "MapviewDemoViewController.h"
#import "LocationDemoViewController.h"
#import "AnnotationDemoViewController.h"
#import "OverlayDemoViewController.h"
#import "PoiSearchDemoViewController.h"
#import "RouteSearchDemoViewController.h"
#import "GeocodeDemoViewController.h"
#import "OfflineDemoViewController.h"
#import "BusLineSearchViewController.h"
@implementation RootViewController


#pragma mark -
#pragma mark View lifecycle


- (void)viewDidLoad {
    [super viewDidLoad];
	
	_demoNameArray = [[NSArray alloc]initWithObjects:
					  @"MapViewDemo",
					  @"LocationDemo",
					  @"AnnotationDemo",
					  @"OverlayDemo",
					  @"PoiSearchDemo",
					  @"RouteSearchDemo",
					  @"ReverseGeocodeDemo",
					  @"OfflineDemo",
                      @"BusLineSearchDemo",
					  //@"TestDemo",
					  nil];
	
	NSMutableArray *array = [[NSMutableArray alloc] init];
	
	MapviewDemoViewController *mapviewDemoController = [[MapviewDemoViewController alloc] init];
	mapviewDemoController.title = @"MapViewDemo";
	
	LocationDemoViewController *locationDemoController = [[LocationDemoViewController alloc] init];
	locationDemoController.title = @"LocationDemo";
	
	AnnotationDemoViewController *annotationDemoController = [[AnnotationDemoViewController alloc] init];
	annotationDemoController.title = @"AnnotationDemo";
	
	OverlayDemoViewController *overlayDemoController = [[OverlayDemoViewController alloc] init];
	overlayDemoController.title = @"OverlayDemo";	
	
	PoiSearchDemoViewController *poiSearchDemoController = [[PoiSearchDemoViewController alloc] init];
	poiSearchDemoController.title = @"PoiSearchDemo";
	
	RouteSearchDemoViewController *routeSearchDemoController = [[RouteSearchDemoViewController alloc] init];
	routeSearchDemoController.title = @"RouteSearchDemo";
	
	GeocodeDemoViewController *geocodeDemoController = [[GeocodeDemoViewController alloc] init];
	geocodeDemoController.title = @"ReverseGeocodeDemo";
	
	//TestViewController *testDemo = [[TestViewController alloc] init];
	//testDemo.title = @"TestDemo";
	
	OfflineDemoViewController *offlineDemo = [[OfflineDemoViewController alloc] init];
	offlineDemo.title = @"OfflineDemo";
    
    BusLineSearchViewController *buslineDemo = [[BusLineSearchViewController alloc] init];
	buslineDemo.title = @"BusLineSearchDemo";
	
	[array addObject:mapviewDemoController];
	[array addObject:locationDemoController];
	[array addObject:annotationDemoController];
	[array addObject:overlayDemoController];
	[array addObject:poiSearchDemoController];
	[array addObject:routeSearchDemoController];
	[array addObject:geocodeDemoController];
	[array addObject:offlineDemo];
    [array addObject:buslineDemo];
	//[array addObject:testDemo];
	_viewControllerArray = array;
	
	
	
	self.title = NSLocalizedString(@"BaiduMapApiDemo", @"BaiduMapApiDemo title");
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
}


/*
- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
}
*/
/*
- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
}
*/
/*
- (void)viewWillDisappear:(BOOL)animated {
	[super viewWillDisappear:animated];
}
*/
/*
- (void)viewDidDisappear:(BOOL)animated {
	[super viewDidDisappear:animated];
}
*/

/*
 // Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
	// Return YES for supported orientations.
	return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
 */


#pragma mark -
#pragma mark Table view data source

// Customize the number of sections in the table view.
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return _demoNameArray.count;
}


// Customize the number of rows in the table view.
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 1;
}


// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *CellIdentifier = @"BaiduMapApiDemoCell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }
    
    cell.textLabel.text = [[_demoNameArray objectAtIndex:indexPath.section] copy];
	// Configure the cell.

    return cell;
}


/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/


/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
    
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source.
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view.
    }   
}
*/


/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath {
}
*/


/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/


#pragma mark -
#pragma mark Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	UIViewController* viewController = [_viewControllerArray objectAtIndex:indexPath.section];
    [self.navigationController pushViewController:viewController animated:YES];
	
	/*
	 DetailViewController *detailViewController = [[DetailViewController alloc] initWithNibName:@"Nib name" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
	 [self.navigationController pushViewController:detailViewController animated:YES];
	 [detailViewController release];
	 */
}


#pragma mark -
#pragma mark Memory management

- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Relinquish ownership any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
    // Relinquish ownership of anything that can be recreated in viewDidLoad or on demand.
    // For example: self.myOutlet = nil;
}


- (void)dealloc {
	[_demoNameArray release];
	[_viewControllerArray release];
    [super dealloc];
}


@end


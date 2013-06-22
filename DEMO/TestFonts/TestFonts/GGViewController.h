//
//  GGViewController.h
//  TestFonts
//
//  Created by Dong Yiming on 6/21/13.
//  Copyright (c) 2013 Dong Yiming. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GGViewController : UIViewController
@property (weak, nonatomic) IBOutlet UILabel *lblFamily;
@property (weak, nonatomic) IBOutlet UILabel *lblName;

@property (weak, nonatomic) IBOutlet UITextField *tf;

-(IBAction)familySegAction:(id)sender;
-(IBAction)nameSegAction:(id)sender;

@end

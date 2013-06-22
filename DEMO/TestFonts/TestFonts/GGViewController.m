//
//  GGViewController.m
//  TestFonts
//
//  Created by Dong Yiming on 6/21/13.
//  Copyright (c) 2013 Dong Yiming. All rights reserved.
//

#import "GGViewController.h"

@interface GGViewController ()

@end

@implementation GGViewController
{
    NSArray *_families;
    NSMutableDictionary     *_namesDic;
    
    int         _currentFamilyIndex;
    NSString    *_currentFamily;
    NSArray     *_currentNamesOfFamily;
    
    int         _currentNameIndex;
    NSString    *_currentName;
    
    UIFont      *_currentFont;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	
    [self initFontsData];
    
    [self updateFamily];
    [self updateFont];
    
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapped)];
    [self.view addGestureRecognizer:tap];
}

-(void)tapped
{
    [_tf resignFirstResponder];
}

-(void)initFontsData
{
    // array
    _families = [UIFont familyNames];
    _namesDic = [NSMutableDictionary dictionaryWithCapacity:_families.count];
    
    
    // loop
    for (NSString *familyName in _families)
    {// font names under family
        NSArray *names = [UIFont fontNamesForFamilyName:familyName];
        
        [_namesDic setObject:names forKey:familyName];
    }
}


-(IBAction)familySegAction:(UIButton *)sender
{
    int index = sender.tag;
    if (index == 0)
    {
        _currentFamilyIndex--;
    }
    else
    {
        _currentFamilyIndex ++;
    }
    
    _currentFamilyIndex = MIN(_currentFamilyIndex, _families.count - 1);
    _currentFamilyIndex = MAX(0, _currentFamilyIndex);
    
    [self updateFamily];
    
    [self updateFont];
}

-(void)updateFamily
{
    _currentFamily = _families[_currentFamilyIndex];
    _currentNamesOfFamily = [_namesDic objectForKey:_currentFamily];
    _currentNameIndex = 0;
    
    _lblFamily.text = _currentFamily;
}

-(IBAction)nameSegAction:(UIButton *)sender
{
    int index = sender.tag;
    if (index == 0)
    {
        _currentNameIndex --;
    }
    else
    {
        _currentNameIndex ++;
    }
    
    int count = _currentNamesOfFamily.count - 1;
    _currentNameIndex = MIN(_currentNameIndex, count);
    _currentNameIndex = MAX(0, _currentNameIndex);
    
    [self updateFont];
}

-(void)updateFont
{
    _currentName = _currentNamesOfFamily.count ? _currentNamesOfFamily[_currentNameIndex] : @"无字体";
    
    _lblName.text = _currentName;
    
    _currentFont = [UIFont fontWithName:_currentName size:40.f];
    UIFont *smallFont = [UIFont fontWithName:_currentName size:15.f];
    _tf.font = _currentFont;
    _lblName.font = _lblFamily.font = smallFont;
    NSLog(@"%@", _currentName);
}

@end

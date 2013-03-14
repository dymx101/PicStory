//
//  TMDViewController.m
//  VideoPlayDemo
//
//  Created by Yim Daniel on 13-2-26.
//  Copyright (c) 2013年 Yim Daniel. All rights reserved.
//

#import "TMDViewController.h"

@interface TMDViewController ()

@end

@implementation TMDViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	
}

-(void)videoThumbnailIsAvailable:(NSNotification *)paramNotification
{
    MPMoviePlayerController *controller = paramNotification.object;
    if (controller && [controller isEqual:self.moviePlayer]) {
        NSLog(@"Thumbnail is available");
        // now get the thumbnail out of the user info dictionary
        UIImage *thumbnail = [paramNotification.userInfo objectForKey:MPMoviePlayerThumbnailImageKey];
        if (thumbnail) {
            // we got the thumbnail image. you can now use it here
        }
    }
}

-(void)startPlayingVideo:(id)paramSender
{
    // first let's construct the URL of the file in our application bundle that needs to get played by the movie player
    NSBundle *mainBundle = [NSBundle mainBundle];
    NSString *urlAsString = [mainBundle pathForResource:@"Sample" ofType:@"m4v"];
    NSURL *url = [NSURL fileURLWithPath:urlAsString];
    
    // if we have already created a movie player before, let's try to stop it
    if (self.moviePlayer) {
        [self stopPlayingVideo:nil];
    }
    
    // now create a new movie player using the URL
    self.moviePlayer = [[MPMoviePlayerController alloc] initWithContentURL:url];
    
    if (self.moviePlayer) {
        // listen for the notification that the movie player sends us whenever it finishes playing an audio file
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(videoHasFinishedPlaying:) name:MPMoviePlayerPlaybackDidFinishNotification object:self.moviePlayer];
        
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(videoThumbnailIsAvailable:) name:MPMoviePlayerThumbnailImageRequestDidFinishNotification object:self.moviePlayer];
        
        NSLog(@"successfully instantiated the movie player");
        
        // scale the movie player to fit the aspect ratio
        self.moviePlayer.scalingMode = MPMovieScalingModeAspectFit;
        // let's start playing the video in full screen mode
        [self.moviePlayer play];
        
        [self.view addSubview:self.moviePlayer.view];
        [self.moviePlayer setFullscreen:YES animated:YES];
        
        // capture the frame at the third second into the movie
        NSNumber *thirdSecondThumbnail = [NSNumber numberWithFloat:3.f];
        // we can ask to capture as many frames as we want. but for now, we are just asking to capture one frame
        NSArray *requestedThumbnails = [NSArray arrayWithObject:thirdSecondThumbnail];
        // ask the movie player to capture this frame for us
        [self.moviePlayer requestThumbnailImagesAtTimes:requestedThumbnails timeOption:MPMovieTimeOptionExact];
    }
    else
    {
        NSLog(@"Failed to instantiante the movie player");
    }
}

-(void)stopPlayingVideo:(id)paramSender
{
    if (self.moviePlayer) {
        [[NSNotificationCenter defaultCenter] removeObserver:self name:MPMoviePlayerPlaybackDidFinishNotification object:self.moviePlayer];
        
        [[NSNotificationCenter defaultCenter] removeObserver:self name:MPMoviePlayerThumbnailImageRequestDidFinishNotification object:self.moviePlayer];
        
        [self.moviePlayer stop];
        
        if ([self.moviePlayer.view.superview isEqual:self.view]) {
            [self.moviePlayer.view removeFromSuperview];
        }
    }
}

-(void)viewDidUnload
{
    self.playButton = nil;
    [self stopPlayingVideo:nil];
    self.moviePlayer = nil;
    
    [super viewDidUnload];
}

#pragma mark - notification handler
-(void)videoHasFinishedPlaying:(NSNotification *)paramNotification
{
    // find out what the reason was for the player to stop
    NSNumber *reason = [paramNotification.userInfo valueForKey:MPMoviePlayerPlaybackDidFinishReasonUserInfoKey];
    if (reason) {
        NSInteger reasonAsInteger = reason.integerValue;
        switch (reasonAsInteger) {
            case MPMovieFinishReasonPlaybackEnded:
            {
                // the movie ended normaly
                break;
            }
                
            case MPMovieFinishReasonPlaybackError:
            {
                // an error happened and the movie ended
                break;
            }
                
            case MPMovieFinishReasonUserExited:
            {
                // the user exited the player
                break;
            }
                
                break;
                
            default:
                break;
        }
        
        NSLog(@"Finish reason = %ld", (long)reasonAsInteger);
        [self stopPlayingVideo:nil];
    }
    
}

@end

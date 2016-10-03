//
//  AppDelegate.m
//  Assignment2_ChrisMoffat
//
//  Created by xcode on 2015-12-06.
//  Copyright (c) 2015 Chris Moffat. All rights reserved.
//

#import "AppDelegate.h"
#import <sqlite3.h>
#import "Data.h"

@interface AppDelegate ()

@end

@implementation AppDelegate

@synthesize databaseName, databasePath, people;

-(void)insertIntoDatabase:(Data *) person
{
    sqlite3 *database;
    
    if(sqlite3_open([self.databasePath UTF8String], &database ) == SQLITE_OK)
    {
        const char *sqlStatement = "insert into entries values (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        sqlite3_stmt *compiledStatement;
        
        if(sqlite3_prepare_v2(database, sqlStatement, -1, &compiledStatement, NULL) == SQLITE_OK) {
            sqlite3_bind_text(compiledStatement, 1, [person.fName UTF8String], -1, SQLITE_TRANSIENT);
            sqlite3_bind_text(compiledStatement, 2, [person.lName UTF8String], -1, SQLITE_TRANSIENT);
            sqlite3_bind_text(compiledStatement, 3, [person.Address UTF8String], -1, SQLITE_TRANSIENT);
            sqlite3_bind_text(compiledStatement, 4, [person.Phone UTF8String], -1, SQLITE_TRANSIENT);
            sqlite3_bind_text(compiledStatement, 5, [person.Email UTF8String], -1, SQLITE_TRANSIENT);
            sqlite3_bind_text(compiledStatement, 6, [person.Age UTF8String], -1, SQLITE_TRANSIENT);
            sqlite3_bind_text(compiledStatement, 7, [person.Gender UTF8String], -1, SQLITE_TRANSIENT);
            sqlite3_bind_text(compiledStatement, 8, [person.dob UTF8String], -1, SQLITE_TRANSIENT);
            sqlite3_bind_text(compiledStatement, 9, [person.avatar UTF8String], -1, SQLITE_TRANSIENT);
        }
        if (sqlite3_step(compiledStatement) != SQLITE_DONE)
            NSLog(@"Error is %s", sqlite3_errmsg(database));
        else
            NSLog(@"Inserted row with id: %lld", sqlite3_last_insert_rowid(database));
        sqlite3_finalize(compiledStatement);
    }
    sqlite3_close(database);
}

-(void)readDataFromDatabase
{
    [self.people removeAllObjects];
    
    sqlite3 *database;
    
    if (sqlite3_open([self.databasePath UTF8String], &database) == SQLITE_OK)
    {
        const char *sqlStatement = "select * from entries;";
        
        sqlite3_stmt *compiledStatement;
        
        if(sqlite3_prepare_v2(database, sqlStatement, -1, &compiledStatement, NULL) == SQLITE_OK)
        {
            while(sqlite3_step(compiledStatement) == SQLITE_ROW)
            {
                NSString *fName = ([NSString stringWithUTF8String:(char*)sqlite3_column_text(compiledStatement, 1)]);
                NSString *lName = ([NSString stringWithUTF8String:(char*)sqlite3_column_text(compiledStatement, 2)]);
                NSString *Address = ([NSString stringWithUTF8String:(char*)sqlite3_column_text(compiledStatement, 3)]);
                NSString *Phone = ([NSString stringWithUTF8String:(char*)sqlite3_column_text(compiledStatement, 4)]);
                NSString *Email = ([NSString stringWithUTF8String:(char*)sqlite3_column_text(compiledStatement, 5)]);
                NSString *Age = ([NSString stringWithUTF8String:(char*)sqlite3_column_text(compiledStatement, 6)]);
                NSString *Gender = ([NSString stringWithUTF8String:(char*)sqlite3_column_text(compiledStatement, 7)]);
                NSString *DOB = ([NSString stringWithUTF8String:(char*)sqlite3_column_text(compiledStatement, 8)]);
                NSString *Avatar = ([NSString stringWithUTF8String:(char*)sqlite3_column_text(compiledStatement, 9)]);
                
                Data *data = [[Data alloc] initWithData:fName thelName:lName theAddress:Address thePhone:Phone theEmail:Email theAge:Age theGender:Gender theDOB:DOB theAvatar:Avatar];
                
                [self.people addObject:data];
            }
        }
        sqlite3_finalize(compiledStatement);
    }
    sqlite3_close(database);
}

-(void)checkAndCreateDatabase
{
    BOOL success;
    
    NSFileManager *fileManager = [NSFileManager defaultManager];
    success = [fileManager fileExistsAtPath:self.databasePath];
    
    if(success)
        return;
    
    NSString *databasePathFromApp = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:self.databaseName];
    [fileManager copyItemAtPath:databasePathFromApp toPath:self.databasePath error:nil];
    
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    // Override point for customization after application launch.
    
    self.people = [[NSMutableArray alloc] init];
    self.databaseName = @"A2database.sql";
    
    NSArray *documentPaths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDir = [documentPaths objectAtIndex:0];
    self.databasePath = [documentsDir stringByAppendingPathComponent:self.databaseName];
    
    [self checkAndCreateDatabase];
    [self readDataFromDatabase];
    
    return YES;
}


- (void)applicationWillResignActive:(UIApplication *)application {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application {
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end

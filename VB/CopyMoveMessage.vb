Sub CopyMoveMessageV2()
    On Error GoTo ProcExit
    
    Dim folderName As String
    folderName = "Chris" 'change this value to the name of the folder
    
    Set Selection = ActiveExplorer.Selection
    Set objDestFolder1 = Application.GetNamespace("MAPI").Folders("ABCDE") _
                        .Folders("CLOSED Items")
    Set objDestFolder2 = Application.GetNamespace("MAPI").Folders("ABCDE") _
                        .Folders("CLOSED Items").Folders(folderName)
                Set objDestFolder3 = Application.GetNamespace("MAPI").Folders("ABCDE") _
                        .Folders("CLOSED Items").Folders(folderName).Folders(folderName)
                    
    For i = 1 To Selection.Count
        DoEvents
        If i = 5 Then
            Exit For
        End If
        
        Set objItem = Selection(i)
        objItem.Move objDestFolder3
    Next
       
    For i = objDestFolder3.Items.Count To 1 Step -1
        DoEvents
        Set currentItem = objDestFolder3.Items(i)
        Set objCopy = currentItem.Copy
        objCopy.Move objDestFolder2
        currentItem.Move objDestFolder1
    Next

ProcExit:
    Exit Sub
    
    Set objDestFolder1 = Nothing
    Set objDestFolder2 = Nothing
    Set objDestFolder3 = Nothing
   
End Sub

Sub CopyMoveMessage()
    On Error GoTo ProcExit
  
    Dim objNS As Outlook.NameSpace
    Dim objDestFolder As Outlook.MAPIFolder
    Dim objDestFolder2 As Outlook.MAPIFolder
    Dim objItem As Object
    Dim objCopy As Object
    Set objNS = Application.GetNamespace("MAPI")
    Set Selection = ActiveExplorer.Selection
     
    ' Set the destination folders
    Set objDestFolder = objNS.Folders("Production Services") _
     .Folders("CLOSED Items").Folders("Chris")
    Set objDestFolder2 = objNS.Folders("Production Services") _
     .Folders("CLOSED Items")
         
    Set objItem = Application.ActiveExplorer.Selection.Item(1)
    
    For i = 1 To Selection.Count
        If i = 5 Then
            Exit For
        End If
        Set objItem = Selection(i)
        
        Set objCopy = objItem.Copy
        objCopy.Move objDestFolder
        objItem.Move objDestFolder2
    Next

ProcExit:
    Exit Sub

    Set objDestFolder = Nothing
    Set objNS = Nothing
End Sub

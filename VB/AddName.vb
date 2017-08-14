Sub AddName()
    On Error GoTo ProcExit
    
    Dim nameValue As String
    nameValue = "Chris Moffat" 'change this value to what name you want to appear in the Analyst field

    Dim strAnalyst As String
    Dim UserDefinedFieldName As String
    Set Selection = ActiveExplorer.Selection
    Set objItem = Application.ActiveExplorer.Selection.Item(1)
    UserDefinedFieldName = "Analyst"
    
    For i = 1 To Selection.Count
        DoEvents
        If i = 5 Then
            Exit For
        End If
        
        Set objItem = Selection.Item(i)
        Set objProperty = objItem.UserProperties.Add(UserDefinedFieldName, Outlook.OlUserPropertyType.olText)
        strAnalyst = objProperty.Value
        objItem.Categories = ""
        
        If strAnalyst = "" Then
            objProperty.Value = nameValue
        ElseIf InStr(strAnalyst, nameValue) > 0 Then
            'Do nothing, name is already present
        Else
            objProperty.Value = objProperty.Value + " " + nameValue
        End If
        objItem.Save
    Next
      
ProcExit:
    Exit Sub
    
End Sub

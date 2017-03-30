Sub Addname()
    On Error GoTo ProcExit

    Dim myolApp As Outlook.Application
    Dim aItem As Object
    Dim oExp As Outlook.Explorer
    Dim UserDefinedFieldName As String
    Dim objProperty As Outlook.UserProperty
    Dim strAnalyst As String
    UserDefinedFieldName = "Analyst"

    'Set current folder as active and set up objects
    Set myolApp = CreateObject("Outlook.Application")
    Set Selection = ActiveExplorer.Selection
    Set aItem = Application.ActiveExplorer.Selection.Item(1)

    'Maximum 4 selections, after 4 it exits. Add current user to Analyst field.
        For i = 1 To Selection.Count
            If i = 5 Then
                Exit For
            End If
            
            Set aItem = Selection.Item(i)
            Set objProperty = aItem.UserProperties.Add(UserDefinedFieldName, Outlook.OlUserPropertyType.olText)
            strAnalyst = objProperty.Value
            
            If strAnalyst = "" Then
                objProperty.Value = "Chris Moffat"
            ElseIf InStr(strAnalyst, "Chris Moffat") > 0 Then
                'Do nothing, name is already present
            Else
                objProperty.Value = objProperty.Value + " " + "Chris Moffat"
            End If
            
            aItem.Save
        Next

    ProcExit:
        Exit Sub

End Sub

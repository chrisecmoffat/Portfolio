Private Sub SetupWorkbook()

    Dim wSheet As Worksheet
    
    For Each wSheet In Worksheets
        wSheet.Protect Password:="prod", UserInterFaceOnly:=True
    Next wSheet

'****************************************************************
'Employee number to name conversion
'For new employees add the following
'****************************************************************
'   If emp = "employee number" Then
'        name = "employee name"
'    End If
'****************************************************************

    Dim emp As String
    Dim name As String

    emp = Environ("username")
    
    If emp = "XXXXXXXX" Then
        name = "NAME"
    End If
    
'Output to spreadsheet. The Cell range is hardcoded.
'If for some reason the cell to be written moves enter new cell value below
            
'User name written to cell
    Sheet02.Range("F3").Value = name
    Sheet03.Range("f3").Value = name
    Sheet04.Range("f3").Value = name
    Sheet05.Range("f3").Value = name
    Sheet06.Range("f3").Value = name
    Sheet07.Range("f3").Value = name
    Sheet08.Range("f3").Value = name
    Sheet09.Range("f3").Value = name
    Sheet10.Range("f3").Value = name
    Sheet11.Range("f3").Value = name
    Sheet12.Range("f3").Value = name
    Sheet13.Range("f3").Value = name
    
'Date - only written if cell is blank to accomodate night shift opening spread sheet after 11:59pm
    If Sheet01.Range("C4").Value = "" Then
        Sheet01.Range("C4").Value = Date
    End If

End Sub

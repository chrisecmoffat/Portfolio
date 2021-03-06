Private Sub Workbook_Open()

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
        name = "Name"
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
    
Sub JobsListCompletion()
    
'Check for empty comment field, exit if true
    If Cells(Rows.count, "G").End(xlUp) = Range("F3").Value And Cells(Rows.count, "G").End(xlUp).Offset(0, 1) = "" Then
        MsgBox "Please comment empty Extra Files field(s)."
        Exit Sub
    End If
    
'Declare Variables for arrays that are used
    Dim AppJobNameList() As String
    Dim AppJobCompareList() As String
    Dim LastRow As Integer
    Dim tempString As String

'Determines Last populated row in column B and resets dimensions for array
    LastRow = Cells(Rows.count, 2).End(xlUp).Row
    ReDim AppJobNameList(1 To LastRow)

'Take values from Column B and populate array
    For i = 1 To LastRow
        AppJobNameList(i) = Cells(i, 2).Value
    Next i

'Takes values input from user popup, separates by space, populates array
'Start by trimming leading/trailing spaces
'Now, while we have 2 consecutive spaces, replace them
'with a single space...
    On Error Resume Next
        tempString = Application.InputBox(Prompt:= _
            "Please enter the completed job list: ", Type:=2)
                tempString = Trim(tempString)
                Do While InStr(1, tempString, "  ")
                    tempString = Replace(tempString, "  ", " ")
                Loop
            AppJobCompareList() = Split(tempString)
    On Error GoTo 0

    Dim m As Long
    Dim n As Long
    Dim count As Integer
    count = 0
    x = False
    y = False

    
     
'Compares the first value in CompareList to the entire column B. Once a match happens, a flag will trigger x variable to TRUE, resulting in highlighted cell and
'alteration of the name in the sheet and temprorary array, making sure that no match can happen for this value again. For loop will kick out on the match, drop to
'an IF statement checking the flag, if TRUE, nothing will happen, flag will reset to FALSE and continue loop. If FALSE, then no match, storing the non-matching
'value from the CompareList into the latest unused cell in row F.
    For n = LBound(AppJobCompareList) To UBound(AppJobCompareList)
        For m = LBound(AppJobNameList) To UBound(AppJobNameList)
            If Not Len(AppJobNameList(m)) = 0 Then
                If AppJobNameList(m) = AppJobCompareList(n) Then
                    x = True
                    Cells(m, 2).Interior.ColorIndex = 35
                    Cells(m, 4).Value = Range("F3").Value
                    AppJobNameList(m) = Cells(m, 2).Value + " "
                    Cells(m, 2).Value = Cells(m, 2).Value + " "
                    Exit For
                End If
            End If
        Next m
        If x = False Then
            If AppJobCompareList(n) <> "False" Then
                y = True
                count = count + 1
                Range("F2").Offset(((Cells(Rows.count, 6).End(xlUp).Row) - 1), 0) = AppJobCompareList(n)
                Range("G1").Offset(((Cells(Rows.count, 6).End(xlUp).Row) - 1), 0) = Range("F3").Value
            End If
        End If
            x = False
    Next n
    
    If y = True Then
        If count > 1 Then
            MsgBox "There are " & count & " Extra Files. Please comment."
        Else
            MsgBox "There is " & count & " Extra File. Please comment."
        End If
    End If
    
End Sub
           
Sub NoSpace_Color()

'Removes selected coloured cell and trims excess spaces
    Dim c As Range
    For Each c In Selection.Cells
        c = Trim(c)
        If c.Interior.ColorIndex <> 15 Then
            If c.Interior.ColorIndex <> 48 Then
            c.Interior.ColorIndex = 2
            End If
        End If
        If c = Range("F3").Value Then
            c = ""
        End If
    Next
    
End Sub

Private Sub Workbook_BeforeClose(Cancel As Boolean)

'Fill in any empty comment fields before closing
    If Cells(Rows.count, "G").End(xlUp) = Range("F3").Value And Cells(Rows.count, "G").End(xlUp).Offset(0, 1) = "" Then
        MsgBox "Please comment empty Extra Files field(s) before closing."
        Cancel = True
    End If
    
End Sub

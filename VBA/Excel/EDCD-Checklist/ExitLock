Private Sub Workbook_BeforeClose(Cancel As Boolean)

Z = False

'Determines Last populated row in column B and resets dimensions for array
    LastRow = Cells(Rows.count, 2).End(xlUp).Row
    For k = 1 To LastRow
        If Cells(k, 2).Interior.ColorIndex <> 35 And Cells(k, 4).Interior.ColorIndex <> 3 And Cells(k, 4).Value = Range("F3").Value Then
            Z = True
        End If
    Next k
    
'Fill in any empty comment fields before closing
    If Cells(Rows.count, "G").End(xlUp) = Range("F3").Value And Cells(Rows.count, "G").End(xlUp).Offset(0, 1) = "" Then
        MsgBox "Please comment empty Extra Files field(s) before closing."
        Cancel = True
    ElseIf Z = True Then
        MsgBox "Please make sure all jobs are highlighted GREEN using the Job Checker button."
        Cancel = True
    End If
    
End Sub

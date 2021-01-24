
  HDR = sopen('sinal.edf','r', 1:7); %%opens the 7 channels present in the EDF file
  [FILE,HDR]=sread(HDR);

    
  HDREOG=sopen('sinal.edf','r', 3); %%channel 3 corresponds to the EOG signal
  [EOG,HDREOG] = sread(HDREOG);
  Picopositivo =  EOG(1739:1857);
  Piconegativo = EOG(452:570);
  Repouso = EOG(27224:28224);
  csvwrite('Repouso.csv', Repouso);
  csvwrite('Picopositivo.csv', Picopositivo);
  csvwrite('Piconegativo.csv', Piconegativo);
  
  

